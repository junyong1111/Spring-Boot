import { apiClinet } from './ApiClient';


// export  const retrieveAllTodosForuserApi = 
//     (username) => apiClinet.get(`/users/${username}/todos`)

// export  const deleteTodoApi = 
// (username, id) => apiClinet.delete(`/users/${username}/todos/${id}`)

// export  const retrieveTodoApi = 
//     (username, id) => apiClinet.get(`/users/${username}/todos/${id}`)

// export  const updateTodoApi = 
// (username, id, todo) => apiClinet.put(`/users/${username}/todos/${id}`, todo)



export  const createPostsApi = 
(posts) => apiClinet.post(`/api/v1/posts`, posts)

// @GetMapping(value = "/api/v1/posts")
export  const returnAllpostsApi = 
(posts) => apiClinet.get(`/api/v1/posts`, posts)

export  const updatePostsApi = 
(id, postsupdaterequestDto) => apiClinet.put(`/api/v1/posts/${id}`, postsupdaterequestDto)

export  const deletePostsApi = 
(id) => apiClinet.delete(`/api/v1/posts/${id}`)
