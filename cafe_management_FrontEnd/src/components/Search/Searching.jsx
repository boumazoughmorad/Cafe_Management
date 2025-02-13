import { memo, useState } from "react"
import {Input, Button} from '@/components/ui'
import { Search } from "lucide-react";
const Searching = ({ onChange }) =>{
    const [search , setSearch] = useState('');
    const handleSubmit = () => {
        onChange({ search });
      
      };
    return (
        
        <div className="  flex flex-row items-center justify-center">
        <div className='flex items-center w-full max-w-lg border border-gray-300 rounded-full overflow-hidden shadow-sm  m-10'>
              <Input
          className='w-full'
          placeholder='Search destinations'
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />

          <Button onClick={handleSubmit} className='rounded-r-full'>
          <Search className='h-4 w-4' />
        </Button>
        </div>
        </div>

    )
}

export default  memo(Searching) 