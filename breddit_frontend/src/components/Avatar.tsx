type AvatarProps = {
   size: number,
   url: string,
   onClick?: () => void,
}

export default function Avatar({size, url, onClick}: AvatarProps) {
   return (
      <div className={`w-${size} h-${size} rounded-xl transition-all duration-200 ease-linear shadow-lg ${onClick ? "cursor-pointer" : ""}`} onClick={onClick}>
         <img className={`w-${size} h-${size} object-cover rounded-xl transition-all duration-200 ease-linear`} src={`data:image/jpeg;base64,${url}`} alt=""/>
      </div>
   )
}