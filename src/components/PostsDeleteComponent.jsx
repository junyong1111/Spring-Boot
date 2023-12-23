import { Formik, Form, } from "formik"
import { deletePostsApi} from "../api/PostsApiService"
import { useNavigate, useParams } from "react-router-dom"

export default function PostsDeleteComponent(){
    const {id} = useParams()
    const navigator = useNavigate()
    function onSubmit(values){
        console.log("DELETE API 연동!!")
        // console.log(id)
        deletePostsApi(id)
        .then(
            (response) => {
                console.log(response)
                alert("글이 성공적으로 삭제되었습니다.") // 팝업 메시지 추가
                navigator('/posts')
            }
        )
        .catch((error) => console.log(error))
    }
    return(   
        <div className="container">
            <h1>게시글 삭제</h1>
            <div>
                <Formik initialValues={ {} }
                enableReinitialize = {true}
                onSubmit={onSubmit}
                
                >
                {
                    (props) => (
                        <Form>
                            <div>
                                <button className='btn btn-warning m-5' type="submit">삭제</button>
                            </div>
                        </Form>
                    )
                }
                </Formik>

            </div>
        </div>


    )
}

