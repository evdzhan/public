require 'test_helper'

class BasketTest < ActiveSupport::TestCase
  fixtures :basket_items
  fixtures :baskets

  setup do
    @test_basket = Basket.new(id: 5000)
  end

  test "fixture_basket_with_one_basket_item" do
    basket = baskets(:one)
    assert_equal 10, basket.total_price
    assert_equal 1, basket.basket_items.length
    assert_equal 'AVIN111111111111', basket.basket_items[0].avin
    assert_equal 'FOX WINES', basket.basket_items[0].supplier
  end

  test "fixture_basket_with_two_basket_items" do
    basket = baskets(:two)
    assert_equal 50, basket.total_price
    assert_equal 2, basket.basket_items.length
    assert_equal 3, basket.basket_items[0].quantity
    assert_equal 'AVIN333333333333', basket.basket_items[0].avin
    assert_equal 'LION WINES', basket.basket_items[0].supplier

    assert_equal 2, basket.basket_items[1].quantity
    assert_equal 'AVIN222222222222', basket.basket_items[1].avin
    assert_equal 'BEAR WINES', basket.basket_items[1].supplier

  end

  test "no_basket_items_total_price_is_zero" do
    assert_equal 0, @test_basket.total_price
  end

  test "one_basket_item_with_1_quantity_total_price_is_price_of_basket_item" do
    @test_basket.add_basket_item('blah', 1337, 1, 'blah')
    assert_equal 1337, @test_basket.total_price
  end

  test "one_basket_item_with_5_quantity_total_price_is_5_times_price_of_basket_item" do
    @test_basket.add_basket_item('blah', 11, 5, 'blah')
    assert_equal 55, @test_basket.total_price
  end

  test "two_basket_items_with_1_quantity_total_price" do
    @test_basket.add_basket_item( 'blah', 25, 1, 'blah')
    @test_basket.add_basket_item( 'blah', 25, 1, 'blah')
    assert_equal 50, @test_basket.total_price
  end

  test "two_basket_items_with_5_quantity_total_price" do
    @test_basket.add_basket_item( 'blah', 1, 5, 'blah')
    @test_basket.add_basket_item( 'blah', 10, 5, 'blah')
    assert_equal 55, @test_basket.total_price
  end

  test "five_basket_items_with_1_quantity_total_price" do
    @test_basket.add_basket_item( 'blah', 1, 1, 'blah')
    @test_basket.add_basket_item( 'blah', 10, 1, 'blah')
    @test_basket.add_basket_item( 'blah', 100, 1, 'blah')
    @test_basket.add_basket_item( 'blah', 1000, 1, 'blah')
    @test_basket.add_basket_item( 'blah', 10000, 1, 'blah')
    assert_equal 11111, @test_basket.total_price
  end

  test "five_basket_items_with_5_quantity_total_price" do
    @test_basket.add_basket_item( 'blah', 1, 5, 'blah')
    @test_basket.add_basket_item( 'blah', 10, 5, 'blah')
    @test_basket.add_basket_item( 'blah', 100, 5, 'blah')
    @test_basket.add_basket_item( 'blah', 1000, 5, 'blah')
    @test_basket.add_basket_item( 'blah', 10000, 5, 'blah')
    assert_equal 55555, @test_basket.total_price
  end


end
