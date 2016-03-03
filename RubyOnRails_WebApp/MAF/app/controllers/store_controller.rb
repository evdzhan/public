require 'will_paginate/array'

class StoreController < ApplicationController
  include CurrentBasket
  before_action :get_wines, :set_basket

=begin
  The index action does gets all wines, sorts them, paginates them and displays them to the user.
=end
  def index
    sort_wines
    paginate_wines
  end

=begin
  The search action reads the query, iterates over the @wines array, and matches any wines that match the query.
  If the query is empty, we display all the wines.
  If the no wines match the query search, we display all the wines, and show message saying nothing is found
  If there are wines that match the search, we display them sorted, and paginated.
=end
  def search
    query = params[:query]
    unless query.to_s.blank?
      search_result = Array.new
      @wines.each do |wine|
        if wine_like(wine, query)
          search_result.push(wine)
        end
      end

      if search_result.length > 0
        @wines = search_result
      else
        flash[:notice] = 'No wines match the search criteria, displaying all wines'
      end
    end
    sort_wines
    paginate_wines
    render 'index'
  end

=begin
  The show action, reads the passed AVIN, and searches through the @wines array to see matches any.
    If matching wine is found, it is assigned to @displayed bottle. Then, the show template reads the displayed bottle,
    displaying all information about that wine.
=end
  def show
    @displayed_bottle = get_displayed_bottle
  end

=begin
  The chec_out action checks if the visitor is logged in,
    if he isn't, he is redirected to the login page.
    if he is, his basket contents are sent to each supplier,
      his basket is emptied, and he is returned to the index

=end
  def check_out
    if user_signed_in?
      process_basket
    else
      redirect_to new_user_session_path, notice: 'Please, login to checkout.'
    end
  end


  private

=begin
  Creates orders for each supplier, that has wines in the users basket.
  If user has wine from two different suppliers, two different orders are dispatched to each supplier.
  The order is dispatched in json format.
=end
  def process_basket
    WinesFromSuppliers.instance.suppliers.each do |supplier|
      basket_items = @basket.basket_items.select { |item| item['supplier'] == supplier.name }
      if basket_items.length > 0
        puts 'Sending a order.'
        HTTParty.post(supplier.order_uri.to_s,
                      body: json_order(basket_items),
                      headers: {'Content-Type' => 'application/json'})
      end
    end
    @basket.destroy
    redirect_to store_index_path, notice: 'Checked out successfully'
  end

=begin
  Turns basket contents to json format, ready to be appended to the json order
=end
  def json_order(basket_items)
    the_order = Hash.new
    the_order['customer_name'] = current_user.name
    the_order['customer_address'] = current_user.address
    the_order['customer_email'] = current_user.email
    the_order['ordered_wines'] = Hash.new
    order_index = 1
    basket_items.each do |item|
      the_order['ordered_wines'][order_index] = Hash.new
      the_order['ordered_wines'][order_index]['avin'] = item.avin
      the_order['ordered_wines'][order_index]['quantity'] = item.quantity
      order_index = order_index + 1
    end
    puts the_order.to_json
    {:the_order => the_order}.to_json
  end

=begin
  Gets the wines from the WinesFromSupplier instance. The wines can be either refetched from the supplier, or
  cached copy can be returned.
=end
  def get_wines
    @wines = WinesFromSuppliers.instance.wines
  end

=begin
  Sorts the wine by the description field
=end
  def sort_wines
    @wines.sort_by! { |wine| wine['description'] }
  end

=begin
  Paginates the wine
=end
  def paginate_wines
    per_page = params[:per_page] || 5
    @wines = @wines.paginate(page: params[:page], per_page: per_page)
  end

=begin
  Gets the avin parameter, and searches for that in the @wine array.
  Returns the individual wine if is found, if it isnt, we redirect to the store index url
=end
  def get_displayed_bottle
    @wines.each do |wine|
      return wine if wine['avin'] == params[:avin]
    end
    redirect_to store_index_url, notice: 'Wine not found'
  end

=begin
  Matched wine against a query string.
=end
  def wine_like(wine, query)
    query_downcased = query.downcase
    ((wine['avin'].to_s.downcase.include? query_downcased) ||
        (wine['description'].to_s.downcase.include? query_downcased) ||
        (wine['supplier'].to_s.downcase.include? query_downcased) ||
        (wine['country'].to_s.downcase.include? query_downcased) ||
        (wine['grape'].to_s.downcase.include? query_downcased))
  end

=begin
  Set the current paginated page, the default is 1
=end
  def set_current_page
    @current_page = params[:page] || 1
  end

end
