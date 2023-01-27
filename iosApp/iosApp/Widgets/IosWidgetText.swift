//
//  IosWidgetText.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 13.01.2023.
//  Copyright © 2023 IceRock Development. All rights reserved.
//

import UIKit
import shared_ios

class IosWidgetText: WidgetText {
    private let root: UITextView = {
        let view = UITextView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.isUserInteractionEnabled = false
        view.backgroundColor = UIColor.clear
        return view
    }()
    
    func isSingleLine(isSingleLine: Bool) {
        // TODO
    }
    
    func textStyle(textStyle: String?) {
        // TODO
    }

    func text(text: String) {
        root.text = text
    }
    
    func textType(textType: EntityTextType?) {
        if(textType == EntityTextType.header){
            root.font = .boldSystemFont(ofSize: 25)
        }
    }
    
    
    var layoutModifiers: Redwood_runtimeLayoutModifier = ExposedKt.layoutModifier()
    
    var value: Any { root }
}
