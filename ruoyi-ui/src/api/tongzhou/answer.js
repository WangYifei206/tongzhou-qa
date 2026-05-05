import request from '@/utils/request'

export function listAnswer(query) {
  return request({
    url: '/tongzhou/answer/list',
    method: 'get',
    params: query
  })
}

export function getAnswer(answerId) {
  return request({
    url: `/tongzhou/answer/${answerId}`,
    method: 'get'
  })
}

export function reviewAnswer(data) {
  return request({
    url: '/tongzhou/answer/review',
    method: 'put',
    data
  })
}

export function offlineAnswer(answerId) {
  return request({
    url: `/tongzhou/answer/offline/${answerId}`,
    method: 'put'
  })
}
