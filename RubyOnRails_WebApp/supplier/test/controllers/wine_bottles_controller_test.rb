require 'test_helper'

class WineBottlesControllerTest < ActionController::TestCase
  setup do
    @wine_bottle = wine_bottles(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:wine_bottles)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create wine_bottle" do
    assert_difference('WineBottle.count') do
      post :create, wine_bottle: { supplier: @wine_bottle.supplier, country: @wine_bottle.country, description: @wine_bottle.description, grape: @wine_bottle.grape, image_url: @wine_bottle.image_url, price: @wine_bottle.price, size: @wine_bottle.size, vegeterians_suitable: @wine_bottle.vegeterians_suitable }
    end

    assert_redirected_to wine_bottle_path(assigns(:wine_bottle))
  end

  test "should show wine_bottle" do
    get :show, id: @wine_bottle
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @wine_bottle
    assert_response :success
  end

  test "should update wine_bottle" do
    patch :update, id: @wine_bottle, wine_bottle: { supplier: @wine_bottle.supplier, country: @wine_bottle.country, description: @wine_bottle.description, grape: @wine_bottle.grape, image_url: @wine_bottle.image_url, price: @wine_bottle.price, size: @wine_bottle.size, vegeterians_suitable: @wine_bottle.vegeterians_suitable }
    assert_redirected_to wine_bottle_path(assigns(:wine_bottle))
  end

  test "should destroy wine_bottle" do
    assert_difference('WineBottle.count', -1) do
      delete :destroy, id: @wine_bottle
    end

    assert_redirected_to wine_bottles_path
  end
end
