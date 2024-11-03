import request from '@/utils/request'



export function getComments(data) {
    return request({
        url: '/Comment/getComments',
        method: 'post',
        data
    })
}
export function getOneComment(data) {
    return request({
        url: '/Comment/getOneComment',
        method: 'post',
        // headers: {
        //     'Content-Type': 'multipart/form-data'
        // },
        processData: false,
        data

    })
}
export function getOneComment1(data) {
    return request({
        url: '/Comment/getOneComment1',
        method: 'post',
        data
    })
}
export function PageAllComment(data) {
    return request({
        url: '/PersonalCenter/getUserCommentList',
        method: 'post',
        data
    })
}
export function getUserReplyList(data) {
    return request({
        url: '/PersonalCenter/getUserReplyList',
        method: 'post',
        data
    })
}
export function getUserComplainList(data) {
    return request({
        url: '/PersonalCenter/getUserComplainList',
        method: 'post',
        data
    })
}
export function getUserFeedbackList(data) {
    return request({
        url: '/PersonalCenter/getUserFeedbackList',
        method: 'post',
        data
    })
}
export function getCommentAll(data) {
    return request({
        url: '/Comment/getCommentAll',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        data
    })
}
export function addcomment(data) {
    return request({
        url: '/Operation/makeComment',
        method: 'post',
        data
    })
}
export function getCommentReplyList(data) {
    return request({
        url: '/Operation/getCommentReplyList',
        method: 'post',
        data
    })
}
export function deleComment(data) {
    return request({
        url: '/PersonalCenter/deleteUserComment',
        method: 'post',
        data
    })
}