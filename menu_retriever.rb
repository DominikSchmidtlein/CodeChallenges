require 'rest-client'
require 'json'

class MenuRetriever
  URL = 'https://backend-challenge-summer-2018.herokuapp.com/challenges.json'

  def retrieve(id)
    menus = []
    page = 1
    begin
      res = RestClient.get(URL, { params: { id: id, page: page }})
      json = JSON.parse(res.body)
      menus += json['menus']
      page += 1
    end until last_page(json['pagination'])
    menus
  end

private

  def last_page(pagination)
    pagination['current_page'] * pagination['per_page'] >= pagination['total']
  end
end
