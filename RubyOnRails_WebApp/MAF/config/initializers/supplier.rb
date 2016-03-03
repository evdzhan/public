class Supplier

  def initialize(wines_uri, order_uri, name, resource_etag = '')
    @wines_uri = wines_uri
    @order_uri = order_uri
    @name = name
    @resource_etag = resource_etag
  end

  attr_reader :wines_uri, :order_uri, :name, :resource_etag
  attr_writer :resource_etag

end