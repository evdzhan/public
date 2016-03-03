ENV['RAILS_ENV'] ||= 'test'
require File.expand_path('../../config/environment', __FILE__)
require 'rails/test_help'

class ActiveSupport::TestCase
  # Setup all fixtures in test/fixtures/*.yml for all tests in alphabetical order.
  fixtures :all

  def negative_numbers
    [-1, -15, -500, -1024, -2000, -5_000_000].freeze
  end

  def positive_numbers
    [1, 25, 322, 766, 5000, 3_000_000].freeze
  end


  def gen_error_message what, field, value, valid
    "#{what} should have been #{ valid ? "" : "in"}valid with '#{field}=#{value}'"
  end
end

class ActionController::TestCase
  include Devise::TestHelpers

  def self.maf_functional_requirements
    @functional_requirements = {
        FR1: 'FR1.Browse non-alcoholic wines',
        FR2: 'FR2.Search',
        FR3: 'FR3.Display detail',
        FR4: 'FR4.Add to basket',
        FR5: 'FR5.Display shopping basket',
        FR6a: 'FR6a.Checkout with log-in',
        FR6b: 'FR6b.Checkout without log-in',
        FR7: 'FR7.Login/logout and registration'}.freeze
  end

end
