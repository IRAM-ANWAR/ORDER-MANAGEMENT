//script for ADD BUTTON
	document.getElementById('.Add').addEventListener('click',function(){
		document.querySelector('.popup').style.display="flex";
		});

	document.getElementById('.close').addEventListener('click',function(){
		document.querySelector('.popup').style.display="none";
		});

	//script for EDIT BUTTON
	function Editform(){
		document.querySelector('.popup-edit').style.display='flex';
	}
	document.querySelector('.close-edit').addEventListener('click',function(){
		document.querySelector('.popup-edit').style.display="none";
		});

    //script for checkbox
    function markRow(rowNumber){
    	let myTable=document.getElementById('customers');
    	let tr=myTable.getElementByTagName('tr');
    	tr.style.backgroundColor="orange";
    }