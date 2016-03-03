module ApplicationHelper


  def truncate_with_hover(text_to_truncate, length = 30)
    "<span title='#{text_to_truncate.gsub("'","\\'")}'>#{truncate(text_to_truncate, :length => length)}</span>" if !text_to_truncate.blank?
  end

  def hidden_div_if(condition, attributes = {}, &block)
    if condition
      attributes['style'] = 'display: none'
    end
    content_tag('div', attributes, &block)
  end

end
