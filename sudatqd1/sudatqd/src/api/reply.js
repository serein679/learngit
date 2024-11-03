import request from '@/utils/request'

export function getReplyS() {
    return request({
        url: '/reply/getReplyS',
        method: 'post'
    })
}
export function deleRe(data) {
    return request({
        url: '/PersonalCenter/deleteUserReply',
        method: 'post',
        data
    })
}
export function addreply(data) {
    return request({
        url: '/Operation/makeReply',
        method: 'post',
        data
    })
}//resourceManagement