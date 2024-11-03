import request from '@/utils/request'

export function addReport(data) {
    return request({
        url: '/Operation/makeReport',
        method: 'post',
        data
    })
}
export function getAuditPageList(data) {
    return request({
        url: '/Audit/getAuditPageList',
        method: 'post',
        data
    })
}
export function getAuditList() {
    return request({
        url: '/Audit/getAuditList',
        method: 'post',
    })
}

export function addReport1(data) {
    return request({
        url: '/reportComment/addReport1',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
} //resourceManagement
export function PageAllReCo(data) {
    return request({
        url: '/reportComment/PageAllReCo',
        method: 'post',
        data
    })
}
export function PageAllReCo3(data) {
    return request({
        url: '/reportComment/PageAllReCo3',
        method: 'post',
        data
    })
}
export function PageAllReCo4(data) {
    return request({
        url: '/reportComment/PageAllReCo4',
        method: 'post',
        data
    })
}
export function PageAllReCo33(data) {
    return request({
        url: '/reportComment/PageAllReCo33',
        method: 'post',
        data
    })
}
export function PageAllReCo44(data) {
    return request({
        url: '/reportComment/PageAllReCo44',
        method: 'post',
        data
    })
}
export function PageAllReRE(data) {
    return request({
        url: '/reportComment/PageAllReRE',
        method: 'post',
        data
    })
}
export function editRe(data) {
    return request({
        url: '/reportComment/editRe',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
} //resourceManagement
export function editRe1(data) {
    return request({
        url: '/reportComment/editRe1',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
} //resourceManagement

export function getOneC(data) {
    return request({
        url: '/reportComment/getOneC',
        method: 'post',
        data
    })
}
export function getOneR(data) {
    return request({
        url: '/reportComment/getOneR',
        method: 'post',
        data
    })
}
export function getCommentShenList(data) {
    return request({
        url: '/Operation/getCommentShenList',
        method: 'post',
        data
    })
}