let img_list = document.querySelectorAll(".carousel > .img > img");

for (i = 0; i < img_list.length; i++) {
  let a = document.createElement("a");
  a.setAttribute("class", "dot");
  a.setAttribute("href", "#img" + i);
  img_list[i].id = "img" + i;
  document.querySelector(".carousel > .span-cont").appendChild(a);
}

let spans = document.querySelectorAll(".span-cont > a");
spans[0].style.backgroundColor = "#5397b4";
for (i = 0; i < spans.length; i++) {
  spans[i].onclick = function () {
    for (y = 0; y < spans.length; y++) {
      spans[y].style.backgroundColor = "rgb(34 34 34)";
    }
    this.style.backgroundColor = "#5397b4";
  };
}
