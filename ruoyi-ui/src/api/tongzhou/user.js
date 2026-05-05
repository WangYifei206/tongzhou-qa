import request from '@/utils/request'

export function listPortalUser(query) {
  return request({
    url: '/tongzhou/user/list',
    method: 'get',
    params: query
  })
}
