class Basket < ActiveRecord::Base
  has_many(:basket_items, dependent: :destroy)

  def add_basket_item(avin, price, quantity, supplier)
    current_item = basket_items.find_by(avin: avin)
    if current_item
      current_item.quantity += quantity
    else
      current_item = basket_items.
          build(avin: avin, price: price, quantity: quantity, supplier: supplier)
    end
    current_item
  end

  def total_price
    basket_items.to_a.sum { |item| item.total_price }
  end
end
