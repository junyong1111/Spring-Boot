import { useEffect, useState } from "react"
import { createPostsComponent } from "./postsNavigate"
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
                console.log(response)
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
                                <th>작성자</th>
                                <th>제목</th>
                                <th>수정 날짜</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                    </thead>
                    <tbody>
                    {
                        posts.map(
                            post => (
                                <tr key = {post.id}>
                                    <td><center>{post.author}</center></td>
                                    <td><center>{post.title}</center></td>
                                    <td><center>{new Intl.DateTimeFormat('ko-KR', options).format(new Date(post.modifiedDate))}</center></td>
                                    <td><button className="btn btn-warning">삭제</button></td>
                                    <td><button className="btn btn-success">수정</button></td>
                                </tr>
                            )
                        )
                    }
                    </tbody>

                </table>
            </div>
            <div>
                <button className="btn btn-success" onClick={
                    () => createPostsComponent(navigator)
                }>게시글 등록</button>
            </div>
        </div>
    )
}
