class AddAvinToWineBottles < ActiveRecord::Migration
  def change
    add_column :wine_bottles, :avin, :string
  end
end
