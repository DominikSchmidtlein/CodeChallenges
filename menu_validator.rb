class MenuValidator
  def validate(nodes)
    unless valid_ids?(nodes.map { |e| e['id']})
      return error('ids are not sequential and unique')
    end
    roots = nodes.select { |e| !e.key?('parent_id') }
    output = { valid_menus: [], invalid_menus: [] }
    roots.each do |root|
      valid, root_id, seen_ids = validate_menu(root, nodes)
      menu = { root_id: root_id, children: seen_ids }
      output[valid ? :valid_menus : :invalid_menus].push(menu)
    end
    output
  end

private

  def error(message)
    { error: message }
  end

  def valid_ids?(ids)
    ids == (1..ids.length).to_a
  end

  def validate_menu(root, menus)
    seen_ids = []
    nodes = [root]
    valid = true
    until nodes.empty?
      curr = nodes.pop
      unless seen_ids.include?(curr['id'])
        seen_ids.push(curr['id'])
        curr['child_ids'].reverse_each do |id|
          child = menus[id - 1]
          nodes.push(child) unless child.nil?
          valid = false unless !child.nil? && child['parent_id'] ==  curr['id']
        end
      else
        valid = false
        seen_ids.push(curr['id'])
      end
    end
    root_id = seen_ids.shift
    return valid, root_id, seen_ids.sort
  end
end
