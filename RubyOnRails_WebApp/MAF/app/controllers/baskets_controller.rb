class BasketsController < ApplicationController

=begin
  Destroys the current basket information, along with the associated basket items.
  Thread is used to make things a bit more fluid for the user
=end
  def destroy
    respond_to do |format|
      format.html { redirect_to store_index_url }
      format.js {}
    end

    Thread.new do
      @basket.destroy if @basket.id == session[:basket_id]
      session[:basket_id] = nil
    end
  end

end
