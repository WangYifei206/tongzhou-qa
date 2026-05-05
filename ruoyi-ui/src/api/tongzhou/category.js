import request from '@/utils/request'

export function listCategory(query) {
  return request({
    url: '/tongzhou/category/list',
    method: 'get',
    params: query
  })
}

export function getCategory(categoryId) {
  return request({
    url: `/tongzhou/category/${categoryId}`,
    method: 'get'
  })
}

export function addCategory(data) {
  return request({
    url: '/tongzhou/category',
    method: 'post',
    data
  })
}

export function updateCategory(data) {
  return request({
    url: '/tongzhou/category',
    method: 'put',
    data
  })
}

export function delCategory(categoryId) {
  return request({
    url: `/tongzhou/category/${categoryId}`,
    method: 'delete'
  })
}
