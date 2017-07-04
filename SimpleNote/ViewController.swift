//
//  ViewController.swift
//  SimpleNote
//
//  Created by 吉训振 on 2017/7/4.
//  Copyright © 2017年 吉训振. All rights reserved.
//

import UIKit
import CoreData

class ViewController: UIViewController {
    @IBOutlet weak var addBtn: UIButton!
    @IBOutlet weak var searchBtn: UIButton!
    @IBOutlet weak var noteListView: UITableView!
    
    var noteList = [Note]()
    
    //Const Info
    let LIST_NOTE_COUNT = 5
    let FETCH_SIZE = 10
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext


    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        let subNote = fetchNote(begin: 0, size: FETCH_SIZE)
        if subNote != nil {
            noteList = noteList + subNote!
        }
        
        let cellNib = UINib(nibName: "NoteItem", bundle: nil)
        noteListView.register(cellNib, forCellReuseIdentifier: "noteItem")
        
        noteListView.delegate = self
        noteListView.dataSource = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func search(_ sender: UIButton) {
    }

    //fetch note from coredata
    func fetchNote(begin: Int, size: Int) -> [Note]? {
        let fetchRequest = NSFetchRequest<Note>(entityName: "Note")
        fetchRequest.fetchLimit = size
        fetchRequest.fetchOffset = begin
        
        do{
            let fetchObject = try context.fetch(fetchRequest)
            
            return fetchObject
        }catch{
            return nil
        }
        
    }
    
    //add note segue prepare
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "openNote" {
            let vc = segue.destination as! NoteDetailController
            vc.delegate = self
            
            if let noteItem = sender as? NoteItem {
                if noteItem.noteContent.text == nil {
                    vc.content = noteItem.noteTitle.text
                }else {
                    vc.content = noteItem.noteTitle.text! + noteItem.noteContent.text!
                }
                vc.indexPath = noteListView.indexPath(for: noteItem)
                vc.status = OpenStatus.MODIFY
            }else {
                vc.status = OpenStatus.NEW
            }
        }
    }
}

extension ViewController: UITableViewDelegate, UITableViewDataSource{
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return noteList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = self.noteListView.dequeueReusableCell(withIdentifier: "noteItem") as! NoteItem
        cell.setContent(note: noteList[indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: "openNote", sender: tableView.cellForRow(at: indexPath))
    }
}

extension ViewController: NoteDetailDelegate{
    func newNote(content: String) {
        do{
            let note = NSEntityDescription.insertNewObject(forEntityName: "Note", into: self.context) as! Note
            note.context = content
            try context.save()
            
            noteList.insert(note, at: 0)
            noteListView.reloadData()
        }catch{
            fatalError("insert error")
        }
    }
    
    func modifyNote(content: String, indexPath: IndexPath) {
        noteList[indexPath.row].context = content
        noteListView.reloadRows(at: [indexPath], with: UITableViewRowAnimation.fade)
    }
    
    func deleteNote(indexPath: IndexPath) {
        do{
            let deleteNote = noteList[indexPath.row]
            context.delete(deleteNote)
            try context.save()
            noteList.remove(at: indexPath.row)
            noteListView.deleteRows(at: [indexPath], with: UITableViewRowAnimation.fade)
            noteListView.reloadData()
        }
        catch{
            fatalError("delet error")
        }
    }
}

