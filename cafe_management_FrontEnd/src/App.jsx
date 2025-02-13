import {Header, Footer} from "@/components/Layout"
import HomePage from "./pages/HomePage"
import { Outlet } from "react-router-dom"
import { useState } from "react"




function App() {
 

  return (
      <div className="">
       <Header />
       <div className=" mt-12">
          <Outlet />
       </div>
        <div className="">
          <Footer />
        </div>
      </div>
  )
}

export default App
