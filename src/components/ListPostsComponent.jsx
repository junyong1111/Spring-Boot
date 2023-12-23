import { useEffect, useState } from "react"
import { createPostsNavi, deletePostsNavi, updatePostsNavi } from "../navigator/postsNavigate"
import { useNavigate } from "react-router-dom"
import { returnAllpostsApi } from "../api/PostsApiService"

export default function ListPostsComponent(){
    const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    const [posts, setPosts] = useState([])
    const navigator = useNavigate()
    useEffect(
        () => returnAllPosts(),
        []
    )

    function returnAllPosts(){
        returnAllpostsApi()
        .then(
            (response) => {
                // console.log(response)
                setPosts(response.data)
            }
        )
        .catch((error) => console.log(error))
    }

    return(
        <div className="container">
            <h1>게시글 목록</h1>

            <div>
                <table className="table">
                    <thead>
                            <tr>
                                <th><center>게시글 번호</center></th>
                                <th><center>작성자</center></th>
                                <th><center>제목</center></th>
                                <th><center>수정 날짜</center></th>
                                <th><center>Delete</center></th>
                                <th><center>Update</center></th>
                            </tr>
                    </thead>
                    <tbody>
                    {
                        posts.map(
                            post => (
                                <tr key = {post.id}>
                                    <td><center>{post.id}</center></td>
                                    <td><center>{post.author}</center></td>
                                    <td><center>{post.title}</center></td>
                                    <td><center>{new Intl.DateTimeFormat('ko-KR', options).format(new Date(post.modifiedDate))}</center></td>
                                    <td><center><button className="btn btn-warning" onClick={
                                        () => deletePostsNavi(navigator, post.id)
                                    }>삭제</button></center></td>
                                    <td><center><button className="btn btn-success" onClick={
                                        () => updatePostsNavi(navigator, post.id)
                                    }>수정</button></center></td>
                                </tr>
                            )
                        )
                    }
                    </tbody>

                </table>
            </div>
            <div>
                <button className="btn btn-success" onClick={
                    () => createPostsNavi(navigator)
                }>게시글 등록</button>
            </div>
        </div>
    )
}
