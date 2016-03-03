json.array!(@wine_bottles) do |wine_bottle|
  json.extract! wine_bottle, :id, :supplier, :image_url, :description, :country, :grape, :vegeterians_suitable, :size, :price, :avin
  json.url wine_bottle_url(wine_bottle, format: :json)
end
