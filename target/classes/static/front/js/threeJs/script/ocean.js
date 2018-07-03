var textureArray = [];
var srcUrls = [];
var pick = "";  //被选中的key
var keys = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ,12];

for (var i = 0; i < 12; ++i) {
    srcUrls.push("js/threeJs/testImage/" + (i).toString() + ".jpg");
}

Init();
function Init() {
    var imgArr = [];
    var count = srcUrls.length;
    for (var i = 0; i < count; ++i) {
        var img = new Image();
        img.src = srcUrls[i];
        imgArr.push(img);
        img.onload = function () {
            if (!--count)
            {
                for (var img of imgArr)
                {
                    var c = document.createElement("canvas");
                    c.width = 128;
                    c.height = 128;
                    var ctx = c.getContext("2d");
                    ctx.drawImage(img, 0, 0, 128, 128);
                    textureArray.push(new THREE.Texture(c));
                }
                console.log(textureArray.length);
                Run(window.innerHeight, window.innerWidth, 40);
            }
        }
    }
}
function Run(height, width, distributionRadius)
{
    var scene, camera, renderer, orbitControl;   //场景，摄像机，渲染器，控制器
    var envmap;       //环境贴图

    var octree;
    var raycaster;  //八叉树,拾取光线

    var mouse;      //鼠标
   

    var unitArray = [];
    


    InitScene();
    InitSkybox();
    AddObject();
    animate();

    function InitScene() {
        scene = new THREE.Scene();
       
        camera = new THREE.PerspectiveCamera(75, width / height, 0.1, 1000)
        camera.position.set(0, 0, -30)  //camera position必须设置
        camera.lookAt(0, 0, 1);
        scene.add(camera);
        renderer = new THREE.WebGLRenderer({ antialias: true });
        renderer.setSize(width, height);

        orbitControl = new THREE.OrbitControls(camera, renderer.domElement);


        octree = new THREE.Octree({ undeferred: true, depthMax: Infinity, objectsThreshold: 8, overlapPct: 0.15 });

        raycaster = new THREE.Raycaster();

        mouse = new THREE.Vector2(0, 0);

        renderer.domElement.addEventListener("click", onDocumentMouseClick, false);
        document.body.appendChild(renderer.domElement);
    }
    function InitSkybox()  //初始化背景
    {
        var path = "js/threeJs/cubetexture/";
        var names = ["px", "nx", "py", "ny", "pz", "nz"];
        var format = ".jpg";
        var urls = [
            path + "px" + format, path + "nx" + format,
            path + "py" + format, path + "ny" + format,
            path + "pz" + format, path + "nz" + format
        ];
        envmap = new THREE.CubeTextureLoader().load(urls);
        scene.background = envmap;
    }
    function AddObject()     //将渲染单元加入到场景中
    {
        for (var i = 0; i < textureArray.length; ++i) {
            var position = new THREE.Vector3(Math.random() * distributionRadius - distributionRadius / 2,
                Math.random() * distributionRadius - distributionRadius / 2,
                Math.random() * distributionRadius - distributionRadius / 2); //正方体内均匀分布
            var sphereRadius = Math.random() * 4 + 1;    //球大小
            var boxSize = sphereRadius * 2 / Math.sqrt(3) * 0.8;  //内接正方体的0.8倍
            textureArray[i].needsUpdate = true;
            unitArray.push(new BasicUnit(position, sphereRadius, envmap, boxSize, textureArray[i]));
           
            unitArray[i].AddToOctree(octree);
            unitArray[i].AddToScene(scene);
            unitArray[i].SetKey(keys[i]);
            unitArray[i].SetIndex(i);
        }
        
    }
    function render() {
        var timer = Date.now() * 0.001;
        for (var i = 0; i < unitArray.length; ++i)
        {
            //textureArray[i].needsUpdate = true;
            unitArray[i].RandomMove(timer, 0.02);
            unitArray[i].InnerBoxRotateY(10);
            unitArray[i].UpdateOctree(octree);
        }
        orbitControl.update();
        renderer.render(scene, camera);
    }
    function onDocumentMouseClick(event)
    {
        mouse.x = ((event.clientX - renderer.domElement.offsetLeft) / width) * 2 - 1;
        mouse.y = - ((event.clientY - renderer.domElement.offsetTop) / height) * 2 + 1;
        raycaster.setFromCamera(mouse, camera);
        var octreeObjects = octree.search(raycaster.ray.origin, raycaster.ray.far, true, raycaster.ray.direction);
        var intersections = raycaster.intersectOctreeObjects(octreeObjects);
        if (intersections.length > 0) {
            pick = intersections[0].object.name;
            console.log(pick);
            window.location.href = "/back/wishbottle/detail/" + pick;
            window.event.returnValue = false;
        }
        else
            pick = "";
      
        
    }
    function animate() {
        requestAnimationFrame(animate);
        render();
        octree.update();
    }

   
}



















