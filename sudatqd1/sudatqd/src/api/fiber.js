import request from '@/utils/request'

export function getFibers() {
    return request({
        url: '/Fiber/getFibers',
        method: 'post'
    })
}
export function getFibers1() {
    return request({
        url: '/Fiber/getFiberList',
        method: 'post'
    })
}
export function getFiberLists() {
    return request({
        url: '/Fiber/getFiberLists',
        method: 'post'
    })
}
export function SelectFiber(data) {
    return request({
        url: '/Fiber/SelectFiber',
        method: 'post',
        data
    })
}
export function getOneFiber(data) {
    return request({
        url: '/Fiber/getOneFiber',
        method: 'post',
        data
    })
}

export function getOneFi(data) {
    return request({
        url: '/Fiber/getFiberDetail',
        method: 'post',
        data
    })
}
export function PageAllFiber(data) {
    return request({
        url: '/Fiber/getFiberPageList',
        method: 'post',
        data
    })
}
export function editFiber(data) {
    return request({
        url: '/Fiber/editFiber',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
}//resourceManagement
export function addFiber(data) {
    return request({
        url: '/Fiber/addFiber',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data'
        },
        processData: false,
        data
    })
}//resourceManagement
export function deleFiber(data) {
    return request({
        url: '/Fiber/changeFiberStatus',
        method: 'post',
        // headers: {
        //     'Content-Type': 'multipart/form-data'
        // },
        processData: false,
        data
    })
}//resourceManagement
