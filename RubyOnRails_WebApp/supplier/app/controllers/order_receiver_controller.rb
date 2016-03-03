class OrderReceiverController < ApplicationController

  def receive_order
    puts 'Received order !'
    puts params[:the_order]
  end

end