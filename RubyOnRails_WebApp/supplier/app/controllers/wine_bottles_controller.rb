class WineBottlesController < ApplicationController
  before_action :set_wine_bottle, only: [:show, :edit, :update, :destroy]

  # GET /wine_bottles
  # GET /wine_bottles.json
  def index
    @wine_bottles = WineBottle.all
    fresh_when @wine_bottles
  end

  # GET /wine_bottles/1
  # GET /wine_bottles/1.json
  def show
    fresh_when @wine_bottle
  end

  # GET /wine_bottles/new
  def new
    @wine_bottle = WineBottle.new
  end

  # GET /wine_bottles/1/edit
  def edit
  end

  # POST /wine_bottles
  # POST /wine_bottles.json
  def create
    @wine_bottle = WineBottle.new(wine_bottle_params)

    respond_to do |format|
      if @wine_bottle.save
        format.html { redirect_to @wine_bottle, notice: 'Wine bottle was successfully created.' }
        format.json { render :show, status: :created, location: @wine_bottle }
      else
        format.html { render :new }
        format.json { render json: @wine_bottle.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /wine_bottles/1
  # PATCH/PUT /wine_bottles/1.json
  def update
    respond_to do |format|
      if @wine_bottle.update(wine_bottle_params)
        format.html { redirect_to @wine_bottle, notice: 'Wine bottle was successfully updated.' }
        format.json { render :show, status: :ok, location: @wine_bottle }
      else
        format.html { render :edit }
        format.json { render json: @wine_bottle.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /wine_bottles/1
  # DELETE /wine_bottles/1.json
  def destroy
    @wine_bottle.destroy
    respond_to do |format|
      format.html { redirect_to wine_bottles_url, notice: 'Wine bottle was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_wine_bottle
      @wine_bottle = WineBottle.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def wine_bottle_params
      params.require(:wine_bottle).permit(:supplier, :image_url, :description, :country, :grape, :vegeterians_suitable, :size, :price, :avin)
    end
end
