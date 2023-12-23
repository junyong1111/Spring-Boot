import { BrowserRouter, Routes, Route } from "react-router-dom"
import WelecomeComponent from "./WelecomeComponent"
import ListPostsComponent from "./ListPostsComponent"
import PostsSaveComponent from "./PostsSaveComponent"
import './PostsApp.css'
import HeaderComponent from "./HeaderComponent"
import PostsUpdateComponet from "./PostsUpdateComponent"
import PostsDeleteComponent from "./PostsDeleteComponent"
export default function PostsApp(){
    return(
        <div className="PostApp">
            <BrowserRouter>
            <HeaderComponent/>
            <Routes>
                <Route path ='/' element={<WelecomeComponent/>}></Route>
                <Route path ='/posts' element={<ListPostsComponent/>}></Route>
                <Route path ='/posts/save' element={<PostsSaveComponent/>}></Route>
                <Route path ='/posts/update/:id' element={<PostsUpdateComponet/>}></Route>
                <Route path ='/posts/delete/:id' element={<PostsDeleteComponent/>}></Route>
                
            </Routes>
            </BrowserRouter>
        </div>
    )
}



