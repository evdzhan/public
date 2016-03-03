require 'test_helper'

class BasketItemTest < ActiveSupport::TestCase
  fixtures :basket_items

  setup do
    @basket_item = basket_items(:one)
  end

  test "fixture_basket_items_are_valid" do
    assert basket_items(:one).valid?
    assert basket_items(:two).valid?
    assert basket_items(:three).valid?
    assert basket_items(:four).valid?
  end

  test "basket_item_negative_quantity_invalid" do
    assert @basket_item.valid?
    negative_numbers.each do |number|
      @basket_item.quantity = number
      assert @basket_item.invalid?, gen_error_message('basket_item', 'quantity', @basket_item.quantity, false)
    end
  end

  test "basket_item_positive_quantity_valid" do
    assert @basket_item.valid?
    positive_numbers.each do |number|
      @basket_item.quantity = number
      assert @basket_item.valid?, gen_error_message('basket_item', 'quantity', @basket_item.quantity, true)
    end
  end

  test "basket_item_zero_quantity_invalid" do
    assert @basket_item.valid?
    @basket_item.quantity = 0
    assert @basket_item.invalid?, gen_error_message('basket_item', 'quantity', @basket_item.quantity, false)
  end

  test "basket_item_negative_price_invalid" do
    assert @basket_item.valid?
    negative_numbers.each do |number|
      @basket_item.price = number
      assert @basket_item.invalid?, gen_error_message('basket_item', 'price', @basket_item.price, false)
    end
  end

  test "basket_item_positive_price_valid" do
    assert @basket_item.valid?
    positive_numbers.each do |number|
      @basket_item.price = number
      assert @basket_item.valid?, gen_error_message('basket_item', 'price', @basket_item.price, true)
    end
  end

  test "basket_item_zero_price_invalid" do
    assert @basket_item.valid?
    @basket_item.price = 0
    assert @basket_item.invalid?, gen_error_message('basket_item', 'price', @basket_item.quantity, false)
  end

  test "basket_item_supplier_must_be_present" do
    assert @basket_item.valid?
    @basket_item.supplier = ''
    assert @basket_item.invalid?, 'supplier must be invalid when it is empty'
    @basket_item.supplier = nil
    assert @basket_item.invalid?, 'supplier must be invalid when it is nil'
    @basket_item.supplier = 'valid_supplier'
    assert @basket_item.valid?
  end

  test "basket_item_avin_must_be_present" do
    assert @basket_item.valid?
    @basket_item.avin = ''
    assert @basket_item.invalid?, 'avin must be invalid when it is empty'
    @basket_item.avin = nil
    assert @basket_item.invalid?, 'avin must be invalid when it is nil'
    @basket_item.avin = 'valid_avin'
    assert @basket_item.valid?
  end

  test "basket_item_total_price" do
    @basket_item.quantity = 5
    @basket_item.price = 5
    assert_equal 25, @basket_item.total_price
    @basket_item.price = 15
    assert_equal 75, @basket_item.total_price
    @basket_item.quantity = 15
    assert_equal 225, @basket_item.total_price
  end


end
