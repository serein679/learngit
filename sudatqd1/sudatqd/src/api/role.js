import request from '@/utils/request'

export function getRolePageList(data) {
    return request({
    url: '/Role/getRolePageList',
    method: 'post',
    data
})
}

export function changeRoleStatus(data) {
    return request({
    url: '/Role/changeRoleStatus',
    method: 'post',
    data
})
}