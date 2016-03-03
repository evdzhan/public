class CreateBasketItems < ActiveRecord::Migration
  def change
    create_table :basket_items do |t|
      t.integer :quantity
      t.string :avin
      t.string :supplier
      t.integer :price
      t.belongs_to :basket, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
