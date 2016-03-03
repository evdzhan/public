Rails.application.routes.draw do

  resources :wine_bottles

  # match incoming orders...
  match 'order_receiver/receive_order' => 'order_receiver_controller/receive_order', :via => [:post]

end
