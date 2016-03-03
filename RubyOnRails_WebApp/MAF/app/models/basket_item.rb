class BasketItem < ActiveRecord::Base

  belongs_to(:basket)
  validates :quantity, numericality: {greater_than_or_equal_to: 1}
  validates :price, numericality: {greater_than: 0} #TODO what minimum makes sense? Ask the MAF product owner!
  validates :quantity, :avin, :supplier, :price, :basket_id, presence: true


  def total_price
    quantity * price
  end

end
