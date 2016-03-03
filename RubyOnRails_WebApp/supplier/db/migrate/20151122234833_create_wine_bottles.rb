class CreateWineBottles < ActiveRecord::Migration
  def change
    create_table :wine_bottles do |t|
      t.string :supplier
      t.string :image_url
      t.string :description
      t.string :country
      t.string :grape
      t.boolean :vegeterians_suitable
      t.decimal :size
      t.decimal :price

      t.timestamps null: false
    end
  end
end
