import request from '@/libs/request'

// 全文搜索
export const apiFullSearch = ( data ) => {
  return request({
    url: '/api/search/full',
    method: 'post',
    data: data
  })
}

// 高级搜索
export const apiAdvancedSearch = ( data ) => {
  return request({
    url: '/api/search/advanced',
    method: 'post',
    data: data
  })
}
