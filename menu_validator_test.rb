#!/usr/bin/env ruby

require 'test/unit'
require 'json'
require './menu_validator'

class MenuValidatorTest < Test::Unit::TestCase
  def setup
    @validator = MenuValidator.new
    @nodes = [{ 'id' => 1, 'child_ids' => [2,3] },
             { 'id' => 2, 'parent_id' => 1, 'child_ids' => [] },
             { 'id' => 3, 'parent_id' => 1, 'child_ids' => [] }]
    @expected = { valid_menus: [], invalid_menus: [] }
  end

  def test_valid
    @expected[:valid_menus].push({ root_id: 1, children: [2, 3] })
    check_result
  end

  def test_loop
    @nodes[2]['child_ids'].push(1)
    @expected[:invalid_menus].push({ root_id: 1, children: [1, 2, 3] })
    check_result
  end

  def test_mismatching_references
    @nodes[2]['parent_id'] = 2
    @expected[:invalid_menus].push({ root_id: 1, children: [2, 3] })
    check_result
  end

  def test_multiparent
    @nodes[1]['child_ids'].push(3)
    @expected[:invalid_menus].push({ root_id: 1, children: [2, 3, 3] })
    check_result
  end

  def test_missing_node
    @nodes[1]['child_ids'].push(4)
    @expected[:invalid_menus].push({ root_id: 1, children: [2, 3] })
    check_result
  end

  def test_example
    @nodes = JSON.parse(File.read('example.json'))['menus']
    @expected = JSON.parse(File.read('example_output.json'))
    assert_equal(@expected.to_json, @validator.validate(@nodes).to_json)
  end

private

  def check_result
    assert_equal(@expected, @validator.validate(@nodes))
  end
end
