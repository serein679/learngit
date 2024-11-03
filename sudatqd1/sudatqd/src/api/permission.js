import request from '@/utils/request'


export function getPermissionPageList(data) {
  return request({
    url: '/Permission/getPermissionPageList',
    method: 'post',
    data
  })
}