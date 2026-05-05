import request from '@/utils/request'

export function getOverview() {
  return request({
    url: '/tongzhou/dashboard/overview',
    method: 'get'
  })
}
