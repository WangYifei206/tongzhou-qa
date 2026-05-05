import request from '@/utils/request'

export function portalLogin(data) {
  return request({
    url: '/portal/auth/login',
    method: 'post',
    data
  })
}

export function portalRegister(data) {
  return request({
    url: '/portal/auth/register',
    method: 'post',
    data
  })
}

export function getPortalCategories() {
  return request({
    url: '/portal/categories',
    method: 'get'
  })
}

export function getPortalQuestions(params) {
  return request({
    url: '/portal/questions',
    method: 'get',
    params
  })
}

export function getPortalHotQuestions() {
  return request({
    url: '/portal/questions/hot',
    method: 'get'
  })
}

export function getPortalQuestionDetail(questionId) {
  return request({
    url: `/portal/questions/${questionId}`,
    method: 'get'
  })
}

export function createPortalQuestion(data) {
  return request({
    url: '/portal/questions',
    method: 'post',
    data
  })
}

export function getMyQuestions() {
  return request({
    url: '/portal/questions/mine',
    method: 'get'
  })
}

export function createPortalAnswer(questionId, data) {
  return request({
    url: `/portal/questions/${questionId}/answers`,
    method: 'post',
    data
  })
}

export function favoriteQuestion(questionId) {
  return request({
    url: `/portal/questions/${questionId}/favorite`,
    method: 'post'
  })
}

export function unfavoriteQuestion(questionId) {
  return request({
    url: `/portal/questions/${questionId}/favorite`,
    method: 'delete'
  })
}

export function getMyFavorites() {
  return request({
    url: '/portal/favorites',
    method: 'get'
  })
}

export function getMyAnswers() {
  return request({
    url: '/portal/answers/mine',
    method: 'get'
  })
}

export function getPortalProfile() {
  return request({
    url: '/portal/user/profile',
    method: 'get'
  })
}

export function updatePortalProfile(data) {
  return request({
    url: '/portal/user/profile',
    method: 'put',
    data
  })
}

export function getPortalSummary() {
  return request({
    url: '/portal/user/summary',
    method: 'get'
  })
}
