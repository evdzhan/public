require 'test_helper'

class BasketItemsControllerTest < ActionController::TestCase
  include CurrentBasket

  setup do
    @basket = baskets(:one)
  end

  test "#{maf_functional_requirements[:FR5]} should create basket item via ajax" do

    assert_difference('BasketItem.count') do
      xhr :post, :create, avin: basket_items(:one).avin, price: basket_items(:one).price, quantity: basket_items(:one).quantity, supplier: basket_items(:one).supplier
    end
    assert_response :success
    assert_select_jquery :html, '#basket' do
      assert_select 'tr#current_item td' do |item|
        assert_equal '1 ×AVIN111111111111£10.00', item.text
      end
    end
  end

  test "#{maf_functional_requirements[:FR5]} should create bassket item via simple get" do

    assert_difference('BasketItem.count') do
      get :create,
          avin: basket_items(:one).avin,
          price: basket_items(:one).price,
          quantity: basket_items(:one).quantity,
          supplier: basket_items(:one).supplier
    end

    assert_redirected_to controller: 'store', action: 'index'
  end

end
