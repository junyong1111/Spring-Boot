import { useNavigate } from "react-router-dom"
import { createPostsComponent } from "./postsNavigate"

export default function WelecomeComponent(){

    const navigate = useNavigate()

    return(
        <div className="WelecomeComponent">
            <h1>스프링 부트로 시작하는 웹 서비스</h1>
            <div>
                <button className="btn btn-success" onClick={
                    () => createPostsComponent(navigate)
                }>글 등록</button>
            </div>
        </div>
    )
}