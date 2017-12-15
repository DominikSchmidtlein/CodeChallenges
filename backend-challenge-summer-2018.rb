#!/usr/bin/env ruby

require './menu_retriever'
require './menu_validator'
require 'json'

id = ARGV[0] || 1

retriever = MenuRetriever.new
validator = MenuValidator.new
output = validator.validate(retriever.retrieve(id))
puts JSON.pretty_generate(output)
