# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)





WineBottle.delete_all


def wine_suppliers
  ['EAST ABER WINES DA BESsSsTTT <3', 'NORTH ABER WINES BETTERR OmG!!'].freeze
end

def create_single_wine(index, supplier)
  price = if supplier == wine_suppliers[0] then
            index % 2 == 0 ? index + 1 : index + 2
          else
            index % 2 == 0 ? index + 2 : index + 1
          end

  WineBottle.create!(image_url: 'genericBottle.png',
                     description: ("Wine type ##{index} " * 10),
                     country: 'Wales',
                     grape: 'Black',
                     vegeterians_suitable: false,
                     size: 1.5,
                     price: price,
                     avin: "AVIN#{index.to_s.rjust(13, '0')}",
                     supplier: supplier)
end

case Rails.env

  when 'development_supplier_1'
    1..99.times do |i|
      create_single_wine(i, wine_suppliers[0])
    end
  when 'development_supplier_2'
    1..99.times do |i|
      create_single_wine(i, wine_suppliers[1])
    end
  else
    raise 'Something is wrong with the seed generation.'
end










