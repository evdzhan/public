class BasketItemsController < ApplicationController
  include CurrentBasket
  before_action(:set_basket, only: [:create])
=begin
  Creates a new basket item. It is created by using the @basket instance variable
=end
  def create

    @basket_item = @basket.add_basket_item(params[:avin], params[:price], params[:quantity].to_i, params[:supplier])

    respond_to do |format|
      if @basket_item.save!
        format.html { redirect_to store_index_url }
        format.js { @current_item = @basket_item }
      else
        format.html { redirect_to store_index_url, notice: 'Could not this itme to the basket' } #TODO better error handling...
      end
    end
  end

end
