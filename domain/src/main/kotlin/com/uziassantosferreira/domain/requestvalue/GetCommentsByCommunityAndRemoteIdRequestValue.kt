package com.uziassantosferreira.domain.requestvalue

data class GetCommentsByCommunityAndRemoteIdRequestValue(val community: String = "",
                                                         val remoteId: String = "",
                                                         val page: String = ""): RequestValues