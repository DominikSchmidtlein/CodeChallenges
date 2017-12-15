import requests

clocks = {
	'types': ['watch', 'clock'],
	'price': 0,
	'tax': 0,
	'shipping': 0,
	'mass_g': 0
}

def calculate_total_cost(products):
	for product in products:
		if product['product_type'].lower() in clocks['types']:
			for variant in product['variants']:
				if variant['available']:
					for i in range(1,4): # check for options
						if variant['option' + str(i)]:
							calculate_variant_cost(variant)

def calculate_variant_cost(variant):
	price = float(variant['price'])
	mass_g = variant['grams']

	clocks['price'] += price
	clocks['mass_g'] += mass_g
	if variant['taxable']:
		clocks['tax'] += price * 0.13
	if variant['requires_shipping']:
		clocks['shipping'] += get_shipping_cost(price, mass_g)


def get_shipping_cost(price, mass_g):
	url_ship = 'https://sandbox.shiphawk.com/api/v4/rates?api_key=1dffdbc1212ea9c1710b0384c79e2553'
	headers_ship = {"Content-Type": "application/json"}
	shipping_data = {
		'items': [{
			"item_type": "parcel",
			"length": "3", # inches
			"width" : "3", # inches
			"height": "2", # inches
		}],
		'origin_address': {
			'zip': '93101'
		},
		'destination_address': {
			'zip': '60060'
		},
		'rate_filter': 'best'
	}

	shipping_data['items'][0]['weight'] = str(mass_g * 0.035274)
	shipping_data['items'][0]['value'] = price
	response = requests.post(url_ship, headers=headers_ship, json=shipping_data)
	rates = response.json()['rates']
	return rates[0]['price']

def main():
	url = 'http://shopicruit.myshopify.com/products.json'
	params = {'page': 1}

	while True:
		response = requests.get(url, params=params)
		products = response.json()['products']
		if not products:
			break
		calculate_total_cost(products)
		params['page'] += 1	
	print clocks

if __name__ == '__main__':
	main()

