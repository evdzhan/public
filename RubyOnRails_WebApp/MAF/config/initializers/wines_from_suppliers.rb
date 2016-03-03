require 'singleton'
class WinesFromSuppliers
  include Singleton

=begin
    Returns list of the suppliers. Make sure you see the config/initializers/supplier.rb

    Each supplier is represented with four fields:
      -order_uri - URI to use when sending the order.
      -wines_uri - URI to use when fetching the wines resource
      -name - Name of the supplier company
      -resource_etag - The ETag the last request to wines_uri contained in the HEAD response

=end
  def suppliers
    @suppliers
  end

=begin
    Get the wines to be used in the Store index view.
    The wines are refreshed (if needed) before they are returned.
=end
  def wines
    puts 'User request thread - gonna check if wines need update'
    refresh_wines_if_needed
    @wines

  end

  private
=begin
    Initialize the suppliers and the wines.
    Start the background thread to continually refresh the wines array.
    For now the refresh happens every 60 seconds (1 minute).
=end
  def initialize
    init_suppliers
    init_wines

    Thread.new do
      loop do
        sleep 60 #TODO Should parameterize this.
        puts 'Back ground thread - gonna check if wines need update'
        refresh_wines_if_needed
      end

    end
  end

=begin
    Initialize suppliers array. This could be moved to a Database,
    but as we have only 2 suppliers, Array will do the job for now.
=end
  def init_suppliers
    @suppliers = Array.new

    @suppliers.push(Supplier.new(URI('http://localhost:5888/wine_bottles.json'),
                                 URI('http://localhost:5888/order_receiver/receive_order'),
                                 'EAST ABER WINES DA BESsSsTTT <3'))

    @suppliers.push(Supplier.new(URI('http://localhost:5999/wine_bottles.json'),
                                 URI('http://localhost:5999/order_receiver/receive_order'),
                                 'NORTH ABER WINES BETTERR OmG!!'))
  end

=begin
    Initialize the wines by creating new array with the data from the suppliers.
=end
  def init_wines
    @wines = Array.new
    refresh_wines_if_needed
  end

=begin
    Go through the @suppliers array. Do a get request to its wines resource.
    Check the head to see if the ETag has changed.
          If it has changed
              refresh the wines from the supplier in the cache
          If it hasn't chaned
              keep the cached wines from that supplier
=end
  def refresh_wines_if_needed
    @suppliers.each do |supplier|
      begin

        supplier_reply = HTTParty.get(supplier.wines_uri, headers: {'If-None-Match' => supplier.resource_etag})

        if (supplier_reply.header.instance_of?(Net::HTTPOK))

          puts "Supplier #{supplier.name} has changed. Invalidating the cached wines."
          get_wines_from_supplier supplier, supplier_reply

        elsif (supplier_reply.header.instance_of?(Net::HTTPNotModified))
          puts "Supplier #{supplier.name} has NOT changed. Keeping cached wines. "
        else
          puts "Failed to fetch wines from supplier #{supplier.name}."
        end


      rescue Errno::ECONNREFUSED
        puts "The supplier #{supplier.name} is down"
      rescue
        puts "Unknown exception occured while talking to supplier  #{supplier.name}"
      end

    end
  end

=begin
    Parse the supplier_reply body to JSON, and use the result to update the @wines array.
=end
  def get_wines_from_supplier(supplier, supplier_reply)

    reply = supplier_reply
    # afterwards we get it passed from refresh if needed
    supplier_wine_bottles = JSON.parse(reply.body)
    supplier_wine_bottles.each do |wine_bottle|
      add_to_wines wine_bottle
    end
    supplier.resource_etag = reply.header['ETag'] # set the initial ETag
  end

=begin
    Go through the @wines array.Check if "added_bottle" is already in the array.
      If it isn't
          add it
      If it is in the list, and added_bottle is cheaper
          replace it
=end
  def add_to_wines(added_bottle)
    same_avin_bottle = nil
    @wines.each do |bottle_already_in|
      if bottle_already_in['avin'] == added_bottle['avin']
        same_avin_bottle = bottle_already_in
        break
      end
    end
    if same_avin_bottle
      if added_bottle['price'] < same_avin_bottle['price']
        @wines.delete(same_avin_bottle)
        @wines.push(added_bottle)
      end
    else
      @wines.push(added_bottle)
    end
  end
end

