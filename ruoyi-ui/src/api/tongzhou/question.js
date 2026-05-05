import request from '@/utils/request'

export function listQuestion(query) {
  return request({
    url: '/tongzhou/question/list',
    method: 'get',
    params: query
  })
}

export function getQuestion(questionId) {
  return request({
    url: `/tongzhou/question/${questionId}`,
    method: 'get'
  })
}

export function reviewQuestion(data) {
  return request({
    url: '/tongzhou/question/review',
    method: 'put',
    data
  })
}

export function offlineQuestion(questionId) {
  return request({
    url: `/tongzhou/question/offline/${questionId}`,
    method: 'put'
  })
}
