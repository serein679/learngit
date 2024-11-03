import request from '@/utils/request'

export function getQuestions() {
    return request({
        url: '/Login/getQuesList',
        method: 'post'
    })
}

export function setQuestionAnswer(data) {
    return request({
        url: '/User/updateOneUser',
        method: 'post',
        data
    })
}

export function getQuestionAnswer(data) {
    return request({
        url: '/User/getMiBao',
        method: 'post',
        data
    })
}

export function updateUserPwdByMiBao(data) {
    return request({
        url: '/User/updateUserPwdByMiBao',
        method: 'post',
        data
    })
}

export function updateUserPwd(data) {
    return request({
        url: '/Login/resetPass',
        method: 'post',
        data
    })
}
