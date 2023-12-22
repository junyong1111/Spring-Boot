import { Formik, Form, ErrorMessage, Field } from "formik"
import { createPostsApi } from "../api/PostsApiService"
import { useState } from "react"
import { useNavigate } from "react-router-dom"



export default function PostsSaveComponent(){

    const [title] = useState('')
    const [content] = useState('')
    const [author] = useState('')
    const navigator = useNavigate()
    

    function onSubmit(values){
        const Posts = {
            author : values.author,
            title : values.title,
            content : values.content
        }
        console.log("CREATE API 연동!!")
        createPostsApi(Posts)
        .then(
            (response) => {
                console.log(response)
                alert("글이 성공적으로 등록되었습니다.") // 팝업 메시지 추가
                navigator('/posts')
            }
        )
        .catch((error) => console.log(error))
        
    }

    function validate(values){
        let errors = {}

        if (values.title=== null || values.title ===''){
            errors.title = 'Enter a valid title'
        }
        
        if (values.content.length <= 5){
            errors.content = 'Enter Content at least 5 character'
        }
        if (values.author=== '' || values.author ===null){
            errors.author = 'Enter a valid author'
        }

        return errors
    }

    return(
        <div className="container">
            <h1>게시글 등록</h1>
            <div>
                <Formik initialValues={ {title, content, author} }
                enableReinitialize = {true}
                onSubmit={onSubmit}
                validate={validate}
                validateOnChange={false} // save 버튼을 누를 때만 검증
                validateOnBlur={false} // save 버튼을 누를 때만 검증
                
                >
                {
                    (props) => (
                        <Form>
                            <ErrorMessage
                                name='title'
                                component='div'
                                className='alert alert-warning'
                            />
                            <ErrorMessage
                                name='content'
                                component='div'
                                className='alert alert-warning'
                            />
                             <ErrorMessage
                                name='author'
                                component='div'
                                className='alert alert-warning'
                            />
                            <fieldset className="form-group">
                                <label>제목</label>
                                <Field type="text" className="form-control" name="title"/>
                            </fieldset>

                            <fieldset className="form-group">
                                <label>작성자</label>
                                <Field type="text" className="form-control" name="author"/>
                            </fieldset>

                            <fieldset className="form-group">
                                <label>내용</label>
                                <Field type="text" className="form-control" name="content"/>
                            </fieldset>

                            <div>
                                <button className='btn btn-success m-5' type="submit">저장</button>
                            </div>
                        </Form>
                    )
                }
                </Formik>

            </div>
        </div>
    )
}