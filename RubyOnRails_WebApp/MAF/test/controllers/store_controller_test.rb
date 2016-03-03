require 'test_helper'

class StoreControllerTest < ActionController::TestCase
  test "#{maf_functional_requirements[:FR1]} - should get index " do
    get :index
    assert_response :success
  end
  test "#{maf_functional_requirements[:FR1]} - should see 5 wines per page by default" do
    get :index
    assert_select '#wines .entry', 5
  end
  test "#{maf_functional_requirements[:FR1]} - should see 1 wine per page with per_page equal to 1" do
    get :index, per_page: 1
    assert_select '#wines .entry', 1
  end
  test "#{maf_functional_requirements[:FR1]} - should see 7 wine per page with per_page equal to 7" do
    get :index, per_page: 7
    assert_select '#wines .entry', 7
  end

  test "#{maf_functional_requirements[:FR1]} - should see images for each wine" do
    get :index
    assert_select '#wines .entry .wineimage a img', 5
  end
  test "#{maf_functional_requirements[:FR1]} - should see description for each wine" do
    get :index
    assert_select '#wines .entry .winedetails .winedesc h3', 5
  end
  test "#{maf_functional_requirements[:FR1]} - should see bottle size for each wine" do
    get :index
    assert_select '#wines .entry .winedetails .winesize p', 5
  end
  test "#{maf_functional_requirements[:FR1]} - should see price for each wine" do
    get :index
    assert_select '#wines .entry .winedetails .price_line .price', 5
  end
  test "#{maf_functional_requirements[:FR1]} - should see supplier for each wine" do
    get :index
    assert_select '#wines .entry .winedetails .winesupplier p', 5
  end
  # spent 1 hour on this test.. its 4 a.m. already... what am i doing with my life ?
  test "#{maf_functional_requirements[:FR1]} - wines should be alphabetically ordered" do
    get :index, per_page: 999999 # make sure we see all wines
    prev = ''
    assert_select '#wines .entry .winedetails .winedesc h3' do |h3s|
      h3s.each do |h3|
        assert (h3.text <=> prev) >= 0,
               "\nWine description \n'#{h3.text}' is alphabetically less than \n'#{prev}' and should have been before it"
        prev = h3.text
      end
    end
    get :index, per_page: 5
  end

  test "#{maf_functional_requirements[:FR2]} - should be able to see search field and button" do
    get :index
    assert_select '#content-header #search-field .navbar-form #query', 1 # the search field
    assert_select '#content-header #search-field .navbar-form .btn', 1 # the button
  end
  test "#{maf_functional_requirements[:FR2]} - getting search with an AVIN displays exactly one wine" do
    get :search, query: 'AVIN0000000000001'
    assert_select '#store #wines .digg_pagination b' do |ele|
      assert_equal '1', ele.text.to_s
    end
    assert_select '#wines .entry', 1
  end
  test "#{maf_functional_requirements[:FR2]} - getting search with descriptions displays 10 wines" do
    get :search, query: 'Wine type #1'
    assert_select '#store #wines .digg_pagination #paginate-info-total' do |ele|
      assert_equal 'Displaying wines 1 - 5 of 11 in total', ele.text.to_s.strip
    end
    assert_select '#wines .entry', 5
  end
  test "#{maf_functional_requirements[:FR2]} - getting search with supplier displays 50 wines" do
    get :search, query: 'EAST ABER WINES DA BESsSsTTT <3'
    assert_select '#store #wines .digg_pagination #paginate-info-total' do |ele|
      assert_equal 'Displaying wines 1 - 5 of 48 in total', ele.text.to_s.strip
    end
  end


  test "#{maf_functional_requirements[:FR3]} - an image on a wine should link to its show page" do
    get :index

    links = Array.new
    assert_select '#wines .entry .wineimage a' do |a|
      a.each do |link|
        links.push link.attribute 'href'
      end
    end

    avins = Array.new
    assert_select '#wines .entry .winedetails .wineavin' do |avin|
      avin.each do |an_avin|
        avins.push an_avin.text.gsub('AVIN:', '').strip
      end
    end

    assert_equal links.length, avins.length
    avins.each_with_index do |avin, index|
      assert links[index].to_s.downcase.include? avin.to_s.downcase
    end
  end
  test "#{maf_functional_requirements[:FR3]} - show page should display all details" do
    get :index
    first_avin = nil
    assert_select '#wines .entry .winedetails .wineavin p' do |p|
      first_avin = p[0].text.gsub('AVIN:', '').strip
    end
    get :show, avin: first_avin
    assert_select '#wine-details', 1
    assert_select '#wine-details h3', 1
    assert_select '#wine-details p', 6 do |p|
      assert p[0].text.include? 'Country of origin:'
      assert p[1].text.include? 'Grape:'
      assert p[2].text.include? 'Suitable for vegetarians:'
      assert p[3].text.include? 'AVIN:'
      assert p[4].text.include? 'Size:'
      assert p[5].text.include? 'Supplier:'
    end

    assert_select '#wine-details .price_line .price b', 1 do |b|
      assert b[0].text.include? 'Price:'
    end


  end
  test "#{maf_functional_requirements[:FR3]} - show page should display add button with quantity" do
    get :index
    first_avin = nil
    assert_select '#wines .entry .winedetails .wineavin p' do |p|
      first_avin = p[0].text.gsub('AVIN:', '').strip
    end
    get :show, avin: first_avin

    assert_select '#wine-details .price_line form', 2 do |form|
      assert_equal '/basket_items', (form[0].attribute 'action').value # the add to basket item form
      assert_equal 'index', (form[1].attribute 'action').value # the back button form
    end

    assert_select '#wine-details .price_line form input', 7 do |form|
      assert_equal 'Add to Basket', (form[5].attribute 'value').value # the add to basket button
      assert_equal 'Back', (form[6].attribute 'value').value # the back button
    end


  end


end
